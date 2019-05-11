package idol.contest.configurations;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import idol.contest.model.AgeGroupDetails;
import idol.contest.model.Contestant;

/**
 * Spring configure Kafka stream for age grouped registrations metrics
 */
@Configuration
public class KafkaStreamsConfig {

    private static final String REGISTRATION_DETAILS_BY_AGE = "registration-details-by-age";

    /**
     * @param appName application id to be assigned to the kafka stream that will give us real time data about the registration metrics
     * @return {@code KafkaStreams}
     */
    @Bean
    public KafkaStreams registrationGroupsStreams(@Value("${registrations.data.app.name} : streams-registrations-count") final String appName) {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appName);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "${registrations.data.app.name}:localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.serdeFrom(Contestant.class));
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());

        final KafkaStreams kafkaStreams = new KafkaStreams(registrationDetailsTopology(), props);
        kafkaStreams.start();

        return kafkaStreams;
    }

    /**
     * Topology for the registration metrics stream.
     *
     * @return {@code Topology}
     */
    @Bean
    public Topology registrationDetailsTopology() {
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<Integer, Contestant> source = builder.stream("streams-plaintext-input");
        final KGroupedStream<String, Contestant> groupedContestantStream = source.filter((age, userData) -> age >= 3 && age <= 45)
            .groupBy((age, user) -> user.groupByAge(), Grouped.with(Serdes.String(), Serdes.serdeFrom(Contestant.class)));
        final KTable<String, Long> groupedContestantKTable = groupedContestantStream.count(Materialized.as("grouped-contestant-store"));
        final KTable<String, Long> aggregatedStream = groupedContestantStream.aggregate(
            () -> 0L,
            (ageGroup, user, aggregate) -> user.getGender().equalsIgnoreCase("male") ? aggregate + 1 : aggregate,
            Materialized.as("aggregated-stream-store"));
        aggregatedStream.join(
            groupedContestantKTable,
            (left, right) -> new AgeGroupDetails(left.intValue(), right.intValue()),
            Materialized.as(REGISTRATION_DETAILS_BY_AGE));

        return builder.build();
    }

}
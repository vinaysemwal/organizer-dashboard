package idol.contest.streams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;

import idol.contest.model.AgeGroupDetails;
import idol.contest.model.Contestant;

public class RegistrationByAgeGroupAggregator {

    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-registrations-count");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.serdeFrom(Contestant.class));
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass());

        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<Integer, Contestant> source = builder.stream("streams-plaintext-input");
        final KGroupedStream<String, Contestant> groupedContestantStream = source.filter((age, userData) -> age >= 3 && age <= 45)
            .groupBy((age, user) -> user.groupByAge(), Grouped.with(Serdes.String(), Serdes.serdeFrom(Contestant.class)));
        final KTable<String, Long> groupedContestantKTable = groupedContestantStream.count(Materialized.as("grouped-contestant-store"));
        final KTable<String, Long> aggregatedStream = groupedContestantStream.aggregate(
            () -> 0L,
            (ageGroup, user, aggregate) -> user.getGender().equalsIgnoreCase("male") ? aggregate + 1 : aggregate,
            Materialized.as("aggregated-stream-store"));
        final KTable<String, AgeGroupDetails> finalResult = aggregatedStream.join(
            groupedContestantKTable,
            (left, right) -> new AgeGroupDetails(left.intValue(), right.intValue()),
            Materialized.as("registration-details-by-age"));
        System.out.println(finalResult.queryableStoreName());
    }

}

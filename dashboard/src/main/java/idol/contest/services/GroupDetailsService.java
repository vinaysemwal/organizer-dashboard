package idol.contest.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import idol.contest.model.ContestantGroupDetails;
import reactor.core.publisher.Flux;

/**
 * Service to push groupwise registration details to the clients using Server Side Event mechanism
 */
@RestController("dashboard")
public class GroupDetailsService {

    private static final Log log = LogFactory.getLog(GroupDetailsService.class);

    private static final String REGISTRATION_DETAILS_BY_AGE = "registration-details-by-age";

    @Autowired
    KafkaStreams registrationGroupsStreams;

    /**
     * Streaming service to send events of registration metrics od the age groups.
     *
     * @return {@code Flux of list of each group registration details}
     */
    @GetMapping(value = "/stream/groups", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<ContestantGroupDetails>> streamGroupDetails() {
        return Flux.just(fetchLocal());
    }

    private List<ContestantGroupDetails> fetchLocal() {
        log.info("running GET on this node");
        final List<ContestantGroupDetails> rsSet = new ArrayList<>();
        ordersStore().all()
            .forEachRemaining((keyValue) -> rsSet.add(new ContestantGroupDetails(keyValue.key, keyValue.value.getTotalCount(), keyValue.value.getMaleCount())));
        return rsSet;

    }

    private ReadOnlyKeyValueStore<String, ContestantGroupDetails> ordersStore() {
        return registrationGroupsStreams.store(REGISTRATION_DETAILS_BY_AGE, QueryableStoreTypes.keyValueStore());
    }

}

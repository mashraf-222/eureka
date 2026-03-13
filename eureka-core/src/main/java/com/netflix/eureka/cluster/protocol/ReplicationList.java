package com.netflix.eureka.cluster.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netflix.discovery.provider.Serializer;

/**
 * @author Tomasz Bak
 */
@Serializer("jackson") // For backwards compatibility with DiscoveryJerseyProvider
public class ReplicationList {
    private final List<ReplicationInstance> replicationList;

    public ReplicationList() {
        this.replicationList = new ArrayList<>();
    }

    @JsonCreator
    public ReplicationList(@JsonProperty("replicationList") List<ReplicationInstance> replicationList) {
        this.replicationList = replicationList;
    }

    public ReplicationList(ReplicationInstance replicationInstance) {
        this(Collections.singletonList(replicationInstance));
    }

    public void addReplicationInstance(ReplicationInstance instance) {
        replicationList.add(instance);
    }

    public List<ReplicationInstance> getReplicationList() {
        return this.replicationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ReplicationList that = (ReplicationList) o;

        List<ReplicationInstance> thisList = this.replicationList;
        List<ReplicationInstance> thatList = that.replicationList;

        if (thisList == thatList) {
            return true;
        }
        if (thisList == null || thatList == null) {
            return false;
        }
        return thisList.equals(thatList);
    }

    @Override
    public int hashCode() {
        return replicationList != null ? replicationList.hashCode() : 0;
    }
}

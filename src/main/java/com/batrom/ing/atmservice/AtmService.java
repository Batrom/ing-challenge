package com.batrom.ing.atmservice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class AtmService {

    List<ATM> execute(final Task[] tasks) {
        final var orderedTasks = new ArrayList<AtmIdForRequestTypeList[]>();

        for (int i = 0; i < tasks.length; i++) {
            final var task = tasks[i];

            final var atmsForRegion = atmsForRegion(orderedTasks, task.region());
            addAtmId(atmsForRegion, task);
        }

        return toResult(orderedTasks);
    }

    private static List<ATM> toResult(final List<AtmIdForRequestTypeList[]> orderedTasks) {
        final var result = new ArrayList<ATM>();
        for (int regionId = 0; regionId < orderedTasks.size(); regionId++) {
            final var orderedTask = orderedTasks.get(regionId);
            if (orderedTask == null) continue;

            addToResultForRequestType(result, regionId, orderedTask[0]);
            addToResultForRequestType(result, regionId, orderedTask[1]);
            addToResultForRequestType(result, regionId, orderedTask[2]);
            addToResultForRequestType(result, regionId, orderedTask[3]);
        }
        return result;
    }

    private static void addToResultForRequestType(final ArrayList<ATM> result, final int regionId, final AtmIdForRequestTypeList atmIds) {
        if (atmIds != null) {
            for (int i = 0; i < atmIds.size(); i++) {
                result.add(new ATM(regionId, atmIds.get(i)));
            }
        }
    }

    private void addAtmId(final AtmIdForRequestTypeList[] atmsForRegion, final Task task) {
        final var order = task.requestType().getOrder();
        final var atmIdForRequestType = atmsForRegion[order];
        if (atmIdForRequestType != null) {
            atmIdForRequestType.add(task.atmId());
        } else {
            final var atmIds = new AtmIdForRequestTypeList();
            atmIds.add(task.atmId());
            atmsForRegion[order] = atmIds;
        }
    }

    private static AtmIdForRequestTypeList[] atmsForRegion(final ArrayList<AtmIdForRequestTypeList[]> orderedTasks, final int region) {
        orderedTasks.ensureCapacity(region); // todo maybe add more than one, check if its growing by one or more
        final var atmsForRegion = orderedTasks.get(region);

        if (atmsForRegion != null) {
            return atmsForRegion;
        } else {
            final var atmIds = new AtmIdForRequestTypeList[4];
            orderedTasks.set(region, atmIds);
            return atmIds;
        }
    }

    private static class AtmIdForRequestTypeList extends ArrayList<Integer> {
    }
}

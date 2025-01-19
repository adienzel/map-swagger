package com.example;

import java.util.Comparator;

public class ReferenceComparator implements Comparator<Reference> {
    @Override
    public int compare(Reference r1, Reference r2) {
        // Compare by local field
        int localComparison = Boolean.compare(r1.isLocal(), r2.isLocal());
        if (localComparison != 0) {
            return localComparison;
        }

        // Compare by remotePart field
        if ((r1.getRemotePart() != null && r2.getRemotePart() != null)) {
            int remotePartComparison = r1.getRemotePart().compareTo(r2.getRemotePart());
            if (remotePartComparison != 0) {
                return remotePartComparison;
            }
        } else if (r1.getRemotePart() == null && r2.getRemotePart() != null)  {
            return -1;
        } else if (r1.getRemotePart() != null && r2.getRemotePart() == null) {
            return 1;
        }

        // Compare by localPart field
        int localPartComparison = r1.getLocalPart().compareTo(r2.getLocalPart());
        if (localPartComparison != 0) {
            return localPartComparison;
        }

        // Compare by entryName field
        return r1.getEntryName().compareTo(r2.getEntryName());
    }
}


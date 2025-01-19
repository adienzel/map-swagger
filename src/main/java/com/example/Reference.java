package com.example;

public class Reference {
    private final boolean local;
    private final String  fileName;
    private final String  remotePart;
    private final String  localPart;
    private final String  entryName;
    private final String  ref;

    public Reference(boolean local, String fileName, String reference, String remotePart, String localPart, String entryName) {
        this.local = local;
        this.fileName = fileName;
        this.remotePart = remotePart;
        this.localPart = localPart;
        this.entryName = entryName;
        this.ref = reference;
    }

    public String getRef() {
        return ref;
    }

    public boolean isLocal() {
        return local;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRemotePart() {
        return remotePart;
    }

    public String getLocalPart() {
        return localPart;
    }

    public String getEntryName() {
        return entryName;
    }

    @Override
    public String toString() {
        return "refernce : " + this.getRef();
    }

}

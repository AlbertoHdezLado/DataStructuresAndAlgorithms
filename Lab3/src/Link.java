public class Link {
    public String ref;
    // in the future there will be more fields
    public Link(String ref) {
        this.ref=ref;
    }
    @Override
    public boolean equals(Object obj) {
        //TODO
        Link given = (Link) obj;
        if (given.ref.equals(this.ref)) {
            return true;
        }
        return false;
    }
    public String toString() {
        return ref;
    }

}

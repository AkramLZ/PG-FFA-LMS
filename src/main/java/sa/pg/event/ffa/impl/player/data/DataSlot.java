package sa.pg.event.ffa.impl.player.data;

public enum DataSlot {
    KILLS("Kills"), DEATHS("Deaths");

    private final String name;

    DataSlot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

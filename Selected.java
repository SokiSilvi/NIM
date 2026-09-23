import java.nio.channels.SelectableChannel;

public class Selected {
    private int from_which_packet;
    private int count_of_stones;
    private int offset = 0;

    public Selected(int from_which_packet, int count_of_stones) {
        this.count_of_stones = count_of_stones;
        this.from_which_packet = from_which_packet;
    }

    public int getCount_of_stones() { return count_of_stones; }
    public int getFrom_which_packet() { return from_which_packet; }

    public void setFrom_which_packet(int from_which_packet) {
        this.from_which_packet = from_which_packet;
    }
    public void setCount_of_stones(int count_of_stones) {
        this.count_of_stones = count_of_stones;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }
}

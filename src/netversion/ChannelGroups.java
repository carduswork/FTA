package netversion;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChannelGroups {
	private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup( GlobalEventExecutor.INSTANCE);

    static void add(Channel channel) {
        CHANNEL_GROUP.add(channel);
    }

    public static ChannelGroupFuture broadcast(Object msg) {
        return CHANNEL_GROUP.writeAndFlush(msg);
    }

    static ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
        return CHANNEL_GROUP.writeAndFlush(msg, matcher);
    }

    public static ChannelGroup flush() {
        return CHANNEL_GROUP.flush();
    }

    static boolean discard(Channel channel) {
        return CHANNEL_GROUP.remove(channel);
    }

    static ChannelGroupFuture disconnect() {
        return CHANNEL_GROUP.disconnect();
    }

    public static ChannelGroupFuture disconnect(ChannelMatcher matcher) {
        return CHANNEL_GROUP.disconnect(matcher);
    }

    static boolean contains(Channel channel) {
        return CHANNEL_GROUP.contains(channel);
    }

    static int size() {
        return CHANNEL_GROUP.size();
    }
}

//package cafe.navy.sculpts.tools;
//
//import cafe.navy.sculpts.user.SculptUser;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.checkerframework.checker.nullness.qual.Nullable;
//
//import java.util.Optional;
//
//public class SculptToolHotbar {
//
//    private static final int TOOL_HOTBAR_SIZE = 9;
//    private final @Nullable SculptTool[] tools;
//    private final @Nullable SculptUser user;
//
//    public SculptToolHotbar(final @NonNull SculptUser user,
//                            final @Nullable SculptTool[] tools) {
//        if (tools.length > TOOL_HOTBAR_SIZE) {
//            throw new RuntimeException("Tools array exceeded size limit!");
//        }
//
//        this.tools = new SculptTool[TOOL_HOTBAR_SIZE];
//        for (int i = 0; i < TOOL_HOTBAR_SIZE; i++) {
//            if (tools[i] != null) {
//                this.tools[i] = tools[i];
//            } else {
//                this.tools[i] = null;
//            }
//        }
//        this.user = user;
//    }
//
//    public SculptToolHotbar(final @NonNull SculptUser user) {
//        this(user, new SculptTool[]{new RotateTool(user)});
//    }
//
//    public @NonNull Optional<SculptTool> getSculptToolId(final int index) {
//        return Optional.ofNullable(this.tools[index]);
//    }
//
//    public void setTool(final @NonNull SculptTool tool, final int index) {
//        this.tools[index] = tool;
//    }
//
//    public void removeTool(final @NonNull SculptTool tool, final int index) {
//        this.tools[index] = null;
//    }
//
//}

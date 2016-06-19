package gd2.gd2render.Utils;

public class MemoryUsage {

    // Free RAM
    private static long sFreeSize = 0L;
    // Total RAM for the app
    private static long sTotalSize = 0L;
    // RAM used by app
    private static long sUsedSize = -1L;
    private static Runtime sInfo = Runtime.getRuntime();

    /**
     * this method calculates the used RAM by getting the total RAM available for the app and subtracting the free RAM for the app
     * @return sUsedSize - the amount of RAM that has been used
     */
    public static long getUsed() {

        try {
            sFreeSize = sInfo.freeMemory();
            sTotalSize = sInfo.totalMemory();
            sUsedSize = sTotalSize - sFreeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sUsedSize;
    }

}

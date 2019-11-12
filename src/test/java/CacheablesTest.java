import com.github.ffcfalcos.cache.CacheManager;
import com.github.ffcfalcos.cache.CacheManagerInterface;
import com.github.ffcfalcos.cache.handler.storage.FileStorageHandlerInterface;
import com.github.ffcfalcos.cache.interceptor.Cacheables;
import com.github.ffcfalcos.cache.interceptor.CacheablesInterface;

class CacheablesTest implements TestInterface, CacheablesInterface {

    private final CacheManagerInterface cacheManager = new CacheManager();
    private final String stringTest = "@Cacheables Test";
    private int calls = 0;

    @Cacheables(key = "testKey")
    private String getValue() {
        calls++;
        return stringTest;
    }

    @Override
    public void run() {
        FileStorageHandlerInterface fileStorageHandler = (FileStorageHandlerInterface) cacheManager.
                getStorageHandler("FileStorageHandler");
        fileStorageHandler.setDirectory(System.getProperty("user.dir")+"/cacheDirectory/");
        String value1 = getValue();
        String value2 = getValue();
        if(value1.equals(value2) && value1.equals(stringTest) && calls == 1) {
            System.out.println("Cache Manager succeed the Cacheables Test");
        } else {
            System.out.println("Cache Manager failed the Cacheables Test");
        }
    }

    @Override
    public CacheManagerInterface getCacheManager() {
        return cacheManager;
    }
}

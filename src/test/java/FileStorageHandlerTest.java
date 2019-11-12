import com.github.ffcfalcos.cache.CacheManager;
import com.github.ffcfalcos.cache.CacheManagerInterface;
import com.github.ffcfalcos.cache.handler.storage.FileStorageHandlerInterface;

class FileStorageHandlerTest implements TestInterface {

    private final CacheManagerInterface cacheManager = new CacheManager();

    @Override
    public void run() {
        FileStorageHandlerInterface fileStorageHandler = (FileStorageHandlerInterface) cacheManager.
                getStorageHandler("FileStorageHandler");
        fileStorageHandler.setDirectory(System.getProperty("user.dir")+"/cacheDirectory/");
        String value = "MyFirstContent";
        String key = "testKey";
        fileStorageHandler.add(key, value, "");
        String cacheValue = (String) fileStorageHandler.get(key);
        if(cacheValue.equals(value)) {
            fileStorageHandler.remove(key);
            cacheValue = (String) fileStorageHandler.get(key);
            if(cacheValue == null) {
                System.out.println("Cache Manager succeed the FileStorageHandler Test");
            }
        }
        System.out.println("Cache Manager failed the FileStorageHandler Test");
    }

}

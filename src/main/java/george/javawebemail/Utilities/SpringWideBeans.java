package george.javawebemail.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SpringWideBeans {

    @Autowired
    public ValueOperations<String, String> lo;

}

import eu.dreamix.ms.entity.User;
import eu.dreamix.ms.kafka.producer.Sender;
import eu.dreamix.ms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Service
public class AccountServiceImpl {

    @Value("${spring.kafka.topic.userCreated}")
    private String ACCOUNT_CREATED_TOPIC;

    private AccountRepository AccountRepository;

    private Sender sender;

    @Autowired
	private TaskProducer taskProducer;

    @Autowired
    UserServiceImpl(UserRepository userRepository, Sender sender) {
        this.userRepository = userRepository;
        this.sender = sender;
    }

    // Register Account
    @Override
    public User registerUser(User input) {
        User createdUser = userRepository.save(input);
        // Send to Account create queue 
        sender.send(ACCOUNT_CREATED_TOPIC, createdUser);
        // send email
        sendEmail(input.getEmail());
        return createdUser;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    private void sendEmail(String e){
        TaskMessage t = new TaskMessage();
        t.setEmailId(e);
        taskProducer.sendNewTask(t);

    }
}
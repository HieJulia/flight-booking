import java.util.List; 
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository; 
import com.mycompany.product.entity.Product; 
 
public interface ProductRepository extends ElasticsearchRepository<Product, String> { 
 
   
} 
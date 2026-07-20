package usecasetest;

import com.surely.challenge.product.input.GetTopExpensiveProductsInput;
import com.surely.challenge.product.model.Product;
import com.surely.challenge.product.output.GetTopProductsGateway;
import com.surely.challenge.product.usecase.GetTopExpensiveProductsUseCase;
import com.surely.challenge.user.exception.UserException;
import com.surely.challenge.user.model.User;
import com.surely.challenge.user.output.GetUserGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetTopExpensiveProductsUseCaseTest {

    GetTopExpensiveProductsInput getTopExpensiveProductsInput;

    @Mock
    GetTopProductsGateway getTopProductsGateway;

    @Mock
    GetUserGateway getUserGateway;

    @BeforeEach
    void setup() {
        getTopExpensiveProductsInput = new GetTopExpensiveProductsUseCase(getTopProductsGateway, getUserGateway);
    }

    @Test
    void getTopProducts_userExists_returnsProductsSuccess() {
        // Arrange
        String documentNumber = "123456";
        User user = User.factoryUser("Juan", "Pérez", documentNumber, "juan@mail.com", false);
        
        List<Product> products = Arrays.asList(
                Product.factoryProduct("Notebook", "Desc", BigDecimal.valueOf(150000)),
                Product.factoryProduct("Celular", "Desc", BigDecimal.valueOf(80000)),
                Product.factoryProduct("Monitor", "Desc", BigDecimal.valueOf(40000)),
                Product.factoryProduct("Teclado", "Desc", BigDecimal.valueOf(10000))
        );

        when(getUserGateway.findByDocument(documentNumber)).thenReturn(user);
        when(getTopProductsGateway.getTopExpensiveProductsByDocument(documentNumber, 4)).thenReturn(products);

        // Act
        List<Product> result = getTopExpensiveProductsInput.getTopProducts(documentNumber);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals("Notebook", result.get(0).getName());
        verify(getTopProductsGateway, times(1)).getTopExpensiveProductsByDocument(documentNumber, 4);
    }

    @Test
    void getTopProducts_userNotExists_throwsUserException() {
        // Arrange
        String documentNumber = "999999";

        when(getUserGateway.findByDocument(documentNumber)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(UserException.class, () -> {
            getTopExpensiveProductsInput.getTopProducts(documentNumber);
        });

        Assertions.assertEquals("No existe un usuario registrado para el documento: " + documentNumber, exception.getMessage());
        verify(getTopProductsGateway, never()).getTopExpensiveProductsByDocument(anyString(), anyInt());
    }
}

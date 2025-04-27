package au.com.demo.transactions.util;

import au.com.demo.transactions.exception.ApiError;
import au.com.demo.transactions.rest.model.TransactionResponse;
import au.com.demo.transactions.rest.model.TransactionsCostResponse;
import au.com.demo.transactions.rest.model.TransactionsVolumeResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

import java.io.IOException;

public class KryoHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final MediaType KRYO = new MediaType("application", "x-kryo");

    private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.register(TransactionResponse.class);
            kryo.register(TransactionsVolumeResponse.class);
            kryo.register(TransactionsCostResponse.class);
            kryo.register(ApiError.class);
            return kryo;
        }
    };

    public KryoHttpMessageConverter() {
        super(KRYO);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(
            Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, IOException {
        Input input = new Input(inputMessage.getBody());
        return kryoThreadLocal.get().readClassAndObject(input);
    }

    @Override
    protected void writeInternal(
            Object object, HttpOutputMessage outputMessage) throws IOException {
        Output output = new Output(outputMessage.getBody());
        kryoThreadLocal.get().writeClassAndObject(output, object);
        output.flush();
        output.close();
    }

    @Override
    protected MediaType getDefaultContentType(Object object) {
        return KRYO;
    }
}
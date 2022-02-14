package groovy;

import com.gitee.pristine.spi.transformer.Transformer;

public class FemaleTransformer implements Transformer {

    // 列gender的下标
    private int genderIndex = 2;

    @Override
    public Object[] transfer(Object[] record) {
        if ("男".equals(record[genderIndex])) {
            return null;
        }
        return record;
    }
}
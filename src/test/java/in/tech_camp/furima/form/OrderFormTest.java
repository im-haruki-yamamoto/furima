package in.tech_camp.furima.form;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class OrderFormTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 正常系の基本テストデータを作成するヘルパーメソッド
     */
    private OrderForm createValidForm() {
        OrderForm form = new OrderForm();
        form.setToken("tok_abcdefghijk00000000000000000");
        form.setPostalCode("123-4567");
        form.setPrefectureId(1);
        form.setCity("横浜市緑区");
        form.setAddress("青山1-1-1");
        form.setBuilding("柳ビル103");
        form.setPhoneNumber("09012345678");
        return form;
    }

    @Nested
    @DisplayName("内容に問題がない場合")
    class WhenValid {

        @Test
        @DisplayName("すべての値が正しく入力されていれば保存できること")
        void すべての値が正しく入力されていれば保存できること() {
            OrderForm form = createValidForm();

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("建物名は空でも保存できること")
        void 建物名は空でも保存できること() {
            OrderForm form = createValidForm();
            form.setBuilding(""); // 建物名を空文字に設定

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertTrue(violations.isEmpty());
        }
    }

    @Nested
    @DisplayName("内容に問題がある場合")
    class WhenInvalid {

        // --- クレジットカード情報(token)のテスト ---

        @Test
        @DisplayName("tokenが空では保存できないこと")
        void tokenが空では保存できないこと() {
            OrderForm form = createValidForm();
            form.setToken(null);

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        // --- 郵便番号(postalCode)のテスト ---

        @Test
        @DisplayName("郵便番号が空では保存できないこと")
        void 郵便番号が空では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPostalCode("");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("郵便番号にハイフンがないと保存できないこと")
        void 郵便番号にハイフンがないと保存できないこと() {
            OrderForm form = createValidForm();
            form.setPostalCode("1234567");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("郵便番号が全角数値では保存できないこと")
        void 郵便番号が全角数値では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPostalCode("１２３-４５６７");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        // --- 都道府県(prefectureId)のテスト ---

        @Test
        @DisplayName("都道府県が未選択では保存できないこと")
        void 都道府県が未選択では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPrefectureId(null);

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        // --- 市区町村(city)のテスト ---

        @Test
        @DisplayName("市区町村が空では保存できないこと")
        void 市区町村が空では保存できないこと() {
            OrderForm form = createValidForm();
            form.setCity("");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        // --- 番地(address)のテスト ---

        @Test
        @DisplayName("番地が空では保存できないこと")
        void 番地が空では保存できないこと() {
            OrderForm form = createValidForm();
            form.setAddress("");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        // --- 電話番号(phoneNumber)のテスト ---

        @Test
        @DisplayName("電話番号が空では保存できないこと")
        void 電話番号が空では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPhoneNumber("");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("電話番号が9桁以下では保存できないこと")
        void 電話番号が9桁以下では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPhoneNumber("090123456"); // 9桁

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("電話番号が12桁以上では保存できないこと")
        void 電話番号が12桁以上では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPhoneNumber("090123456789"); // 12桁

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("電話番号にハイフンが含まれていると保存できないこと")
        void 電話番号にハイフンが含まれていると保存できないこと() {
            OrderForm form = createValidForm();
            form.setPhoneNumber("090-1234-5678");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("電話番号が全角数値では保存できないこと")
        void 電話番号が全角数値では保存できないこと() {
            OrderForm form = createValidForm();
            form.setPhoneNumber("０９０１２３４５６７８");

            Set<ConstraintViolation<OrderForm>> violations = validator.validate(form);
            assertFalse(violations.isEmpty());
        }
    }
}
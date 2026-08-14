const pay = () => {

  const payjp = Payjp(PAYJP_PUBLIC_KEY);
  const elements = payjp.elements();

  // カード入力フォーム要素の生成
  const numberElement = elements.create('cardNumber');
  const expiryElement = elements.create('cardExpiry');
  const cvcElement = elements.create('cardCvc');

  // divタグにマウント
  numberElement.mount('#number-form');
  expiryElement.mount('#expiry-form');
  cvcElement.mount('#cvc-form');

  const form = document.getElementById('charge-form');
  if (!form) return;

  form.addEventListener("submit", (e) => {
    e.preventDefault();

    payjp.createToken(numberElement).then((response) => {
      if (response.error) {
        console.error("トークン化に失敗しました:", response.error.message);
        alert("カード情報の入力に誤りがあります。確認してください。");
      } else {
        const token = response.id;
        const tokenObj = `<input value="${token}" name="token" type="hidden">`;
        form.insertAdjacentHTML("beforeend", tokenObj);

        // 入力項目のクリア
        numberElement.clear();
        expiryElement.clear();
        cvcElement.clear();

        // フォーム送信
        form.submit();
      }
    });
  });
};

window.addEventListener("load", pay);
const pay = () => {
  const payjp = Payjp(PAYJP_PUBLIC_KEY)
  const elements = payjp.elements();
  const numberElement = elements.create('cardNumber');
  const expiryElement = elements.create('cardExpiry');
  const cvcElement = elements.create('cardCvc');

  numberElement.mount('#number-form');
  expiryElement.mount('#expiry-form');
  cvcElement.mount('#cvc-form');

  const form = document.getElementById('charge-form');
  form.addEventListener("submit", (e) => {
    e.preventDefault();

    payjp.createToken(numberElement).then(function (response) {
      if (response.error) {
        console.error("トークン化に失敗しました:", response.error.message);
        alert("カード情報の入力に誤りがあります。確認してください。");
      } else {
        const token = response.id;
        const renderDom = document.getElementById("charge-form");
        const tokenObj = `<input value="${token}" name="token" type="hidden">`;
        renderDom.insertAdjacentHTML("beforeend", tokenObj);
        numberElement.clear();
        expiryElement.clear();
        cvcElement.clear();
        document.getElementById("charge-form").submit();
      }});
      e.preventDefault();
  });
};

window.addEventListener("load", pay);
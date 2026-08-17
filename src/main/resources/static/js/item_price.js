window.addEventListener('DOMContentLoaded', () => {
  const priceInput = document.getElementById("item-price");
  if (!priceInput) return;

  priceInput.addEventListener("input", () => {
    const inputValue = priceInput.value;
    const addTaxDom = document.getElementById("add-tax-price");
    const profitDom = document.getElementById("profit");

    if (inputValue >= 300 && inputValue <= 9999999) {
      const fee = Math.floor(inputValue * 0.1);
      addTaxDom.innerHTML = fee;
      profitDom.innerHTML = Math.floor(inputValue - fee);
    } else {
      addTaxDom.innerHTML = '';
      profitDom.innerHTML = '';
    }
  });
});
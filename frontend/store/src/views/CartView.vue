<template>
  <div class="cart-page">
    <div class="column-labels">
      <label class="product-details">Product</label>
      <label class="product-price">Price</label>
      <label class="product-quantity">Quantity</label>
      <label class="product-removal">Remove</label>
      <label class="product-line-price">Total</label>
    </div>

    <div class="product" v-for="(item, index) in cartItems" :key="index">
      <div class="product-details">
        <div class="product-title">{{ item.name }}</div>
      </div>
      <div class="product-price">{{ item.price }}</div>
      <div class="product-quantity">
        <input type="number" v-model.number="item.quantity" min="1">
      </div>
      <div class="product-removal">
        <button @click="removeItem(index)" class="btn btn-danger">
          Remove
        </button>
      </div>
      <div class="product-line-price">{{ (item.price * item.quantity).toFixed(2) }}</div>
    </div>

    <div class="totals">
      <div class="totals-item totals-item-total">
        <label>Grand Total</label>
        <div class="totals-value" id="cart-total">{{ grandTotal.toFixed(2) }}</div>
      </div>
    </div>
      
    <button class="checkout" v-if="grandTotal > 0">Checkout</button>
  </div>
</template>

<script>
import { ref, computed } from 'vue';

export default {
  name: 'CartPage',
  setup() {
    const cartItems = ref([
      // 예시 상품 데이터
      { name: 'Product 1', price: 12.99, quantity: 2 },
      { name: 'Product 2', price: 45.99, quantity: 1 }
    ]);

    const grandTotal = computed(() =>
      cartItems.value.reduce((total, item) => total + item.price * item.quantity, 0)
    );

    function removeItem(index) {
      cartItems.value.splice(index, 1);
    }

    return {
      cartItems,
      grandTotal,
      removeItem
    };
  }
};
</script>


<style scoped>

/*
I wanted to go with a mobile first approach, but it actually lead to more verbose CSS in this case, so I've gone web first. Can't always force things...

Side note: I know that this style of nesting in SASS doesn't result in the most performance efficient CSS code... but on the OCD/organizational side, I like it. So for CodePen purposes, CSS selector performance be damned.
*/
/* Global settings */
/* Global "table" column settings */
.product-image {
  float: left;
  width: 20%;
}

.product-details {
  float: left;
  width: 37%;
}

.product-price {
  float: left;
  width: 12%;
}

.product-quantity {
  float: left;
  width: 10%;
}

.product-removal {
  float: left;
  width: 9%;
}

.product-line-price {
  float: left;
  width: 12%;
  text-align: right;
}

/* This is used as the traditional .clearfix class */
.group:before, .shopping-cart:before, .column-labels:before, .product:before, .totals-item:before,
.group:after,
.shopping-cart:after,
.column-labels:after,
.product:after,
.totals-item:after {
  content: "";
  display: table;
}

.group:after, .shopping-cart:after, .column-labels:after, .product:after, .totals-item:after {
  clear: both;
}

.group, .shopping-cart, .column-labels, .product, .totals-item {
  zoom: 1;
}

/* Apply clearfix in a few places */
/* Apply dollar signs */
.product .product-price:before, .product .product-line-price:before, .totals-value:before {
  content: "$";
}

/* Body/Header stuff */
body {
  padding: 0px 30px 30px 20px;
  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-weight: 100;
}

h1 {
  font-weight: 100;
}

label {
  color: #aaa;
}

.shopping-cart {
  margin: 0 auto; /* 상하 마진을 0으로, 좌우 마진을 auto로 설정하여 가운데 정렬 */
  padding-top: 20px;
  border-top: 1px solid #eee;
  width: 1000px; /* shopping-cart의 너비를 전체 화면의 80%로 설정 */
  max-width: 1000px; /* 최대 너비를 1000px로 설정하여 너무 큰 화면에서도 적절한 레이아웃 유지 */
}

/* Column headers */
.column-labels label {
  padding-bottom: 15px;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
}
.column-labels .product-image, .column-labels .product-details, .column-labels .product-removal {
  text-indent: -9999px;
}

/* Product entries */
.product {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
.product .product-image {
  text-align: center;
}
.product .product-image img {
  width: 100px;
}
.product .product-details .product-title {
  margin-right: 20px;
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
}
.product .product-details .product-description {
  margin: 5px 20px 5px 0;
  line-height: 1.4em;
}
.product .product-quantity input {
  width: 40px;
}
.product .remove-product {
  border: 0;
  padding: 4px 8px;
  background-color: #c66;
  color: #fff;
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
  font-size: 12px;
  border-radius: 3px;
}
.product .remove-product:hover {
  background-color: #a44;
}

/* Totals section */
.totals .totals-item {
  float: right;
  clear: both;
  width: 100%;
  margin-bottom: 10px;
}
.totals .totals-item label {
  float: left;
  clear: both;
  width: 79%;
  text-align: right;
}
.totals .totals-item .totals-value {
  float: right;
  width: 21%;
  text-align: right;
}
.totals .totals-item-total {
  font-family: "HelveticaNeue-Medium", "Helvetica Neue Medium";
}

.checkout {
  float: right;
  border: 0;
  margin-top: 20px;
  padding: 6px 25px;
  background-color: #6b6;
  color: #fff;
  font-size: 25px;
  border-radius: 3px;
}

.checkout:hover {
  background-color: #494;
}
.product-fadeout {
    transition: opacity 0.5s ease, max-height 0.5s ease;
    opacity: 0;
    max-height: 0;
    overflow: hidden;
}

/* Make adjustments for tablet */
@media screen and (max-width: 650px) {
  .shopping-cart {
    margin: 0;
    padding-top: 20px;
    border-top: 1px solid #eee;
   
  }
  .column-labels {
    display: none;
  }
  .product-image {
    float: right;
    width: auto;
  }
  .product-image img {
    margin: 0 0 10px 10px;
  }
  .product-details {
    float: none;
    margin-bottom: 10px;
    width: auto;
  }
  .product-price {
    clear: both;
    width: 70px;
  }
  .product-quantity {
    width: 100px;
  }
  .product-quantity input {
    margin-left: 20px;
  }
  .product-quantity:before {
    content: "x";
  }
  .product-removal {
    width: auto;
  }
  .product-line-price {
    float: right;
    width: 70px;
  }
}
/* Make more adjustments for phone */
@media screen and (max-width: 350px) {
  .product-removal {
    float: right;
  }
  .product-line-price {
    float: right;
    clear: left;
    width: auto;
    margin-top: 10px;
  }
  .product .product-line-price:before {
    content: "Item Total: $";
  }
  .totals .totals-item label {
    width: 60%;
  }
  .totals .totals-item .totals-value {
    width: 40%;
  }
}
.cart-page {
  width: 80%;
  margin: auto;
}
/*# sourceMappingURL=style.css.map */
.product-details, .product-price, .product-quantity, .product-removal, .product-line-price {
  float: left;
  text-align: left;
}

/* 전체 product 항목을 product-line-price에 맞춥니다. */
.product, .totals, .checkout {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.product-details {
  width: 40%; /* 조정이 필요할 수 있음 */
}

.product-price {
  width: 15%; /* 조정이 필요할 수 있음 */
}

.product-quantity {
  width: 15%; /* 조정이 필요할 수 있음 */
}

.product-removal {
  width: 10%; /* 조정이 필요할 수 있음 */
}

.product-line-price {
  width: 20%; /* 조정이 필요할 수 있음 */
  text-align: right;
}

.totals-item, .checkout {
  flex-basis: 100%;
}

.checkout {
  justify-content: flex-end;
  padding: 6px 25px;
  background-color: #6b6;
  color: #fff;
  font-size: 25px;
  border-radius: 3px;
  border: none;
  cursor: pointer;
}

.checkout:hover {
  background-color: #494;
}
</style>
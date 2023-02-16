import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Order } from 'src/app/common/order';
import { OrderItem } from 'src/app/common/order-item';
import { Purchase } from 'src/app/common/purchase';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { IntikompFormService } from 'src/app/services/intikomp-form.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit{

  checkoutFormGroup!: FormGroup;

  totalPrice: number = 0;
  totalQuantity: number = 0;

  creditCardYears: number[] =[];
  creditCardMonths: number[] = [];

  constructor(private formBuilder: FormBuilder,
              private inikompFormService: IntikompFormService,
              private cartService: CartService,
              private checkoutService: CheckoutService,
              private router: Router) {}

  ngOnInit(): void {

    this.reviewCartDetails();
    
    this.checkoutFormGroup = this.formBuilder.group({
      customer: this.formBuilder.group({
        firstName: [''],
        lastName:[''],
        email:['']
      }),
      shippingAdress: this.formBuilder.group({
        street: [''],
        city: [''],
        state: [''],
        country: [''],
        zipCode: ['']
      }),
      creditCard: this.formBuilder.group({
        cardType: [''],
        nameOnCard: [''],
        cardNumber: [''],
        securityCode: [''],
        expirationMonth: [''],
        expirationYear: ['']
      }),
    });

    const startMonth: number = new Date().getMonth() + 1;
    this.inikompFormService.getCreditCardMonths(startMonth).subscribe(
      data => {
        this.creditCardMonths = data;
      }
    );

    this.inikompFormService.getCreditCardYears().subscribe(
      data => {
        this.creditCardYears = data;
      }
    );
  
  }
  reviewCartDetails() {
    this.cartService.totalQuantity.subscribe(
      totalQuantity => this.totalQuantity = this.totalQuantity
    );

    this.cartService.totalPrice.subscribe(
      totalPrice => this.totalPrice = this.totalPrice
    );
  }

  onSubmit() {

    if(this.checkoutFormGroup.invalid) {
    this.checkoutFormGroup.markAllAsTouched();
    return;
    }

    let order = new Order(this.totalPrice, this.totalQuantity);

    const cartItems = this.cartService.cartItems;

    let orderItems: OrderItem[] = [];
    for(let i=0; i < cartItems.length; i++) {
      orderItems[i] = new OrderItem(cartItems[i]);
    }

    let orderItemsShort: OrderItem[] = cartItems.map(tempCartItem => new OrderItem(tempCartItem));

    let purchase = new Purchase(this.checkoutFormGroup.controls['customer'].value,
                                this.checkoutFormGroup.controls['shippingAddress'].value, order, orderItems);

    this.checkoutService.placeOrder(purchase).subscribe(
      {
        next: response => {
          alert(`Twoje zamówienie zostało odebrane.\nNumer zamówienia: ${response.orderTrackingNumber}`);
          this.resetCart();
        },
        error: err => {
          alert(`Wystąpił błąd: ${err.message}`);
        }
      }
    );
 
  }
  resetCart() {
    this.cartService.cartItems = [];
    this.cartService.totalPrice.next(0);
    this.cartService.totalQuantity.next(0);

    this.checkoutFormGroup.reset();

    this.router.navigateByUrl("/products");
  }

}

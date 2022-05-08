import {Component, ElementRef, OnInit} from '@angular/core';
import {Product} from "../Model/Product";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MessageService} from "primeng/api";
import {ActivatedRoute} from "@angular/router";
import {ProductRestService} from "../services/rest/product/product-rest.service";
import {ShoppingListItem} from "../Model/ShoppingListItem";

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrls: ['./shopping-list.component.css']
})
export class ShoppingListComponent implements OnInit {

  shoppingListItems: Product[] = [];
  shoppingListArr: ShoppingListItem[] = [];
  newItem = '';
  productNames: string[] = [];
  dropDownNames: string[] = [];

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private messageService: MessageService,
              private route: ActivatedRoute,
              private productRestService: ProductRestService) {
    this.form = fb.group({
      checkedProducts: new FormArray([])
    });
  }

  ngOnInit(): void {
    this.productNames = this.route.snapshot.data['productNames'];
    this.shoppingListArr = this.route.snapshot.data['shoppingList'];
    if(this.shoppingListArr.length > 0) {
      this.shoppingListArr.forEach(pn => {
        let p = new Product();
        p.name = pn.productName;
        p.done = !pn.active;
        p.id = pn.id
        this.shoppingListItems.push(p);
      });
    }
  }

  selectItem(name: string) {
    this.newItem = name;
    this.dropDownNames = [];
  }

  searchProd() {
    this.dropDownNames = [];
    if (this.newItem !== '') {
      this.productNames.forEach(n => {
        if (n.toUpperCase().includes(this.newItem.toUpperCase())) {
          this.dropDownNames.push(n);
        }
      })
    }
  }

  closeDropDown() {
    this.dropDownNames = [];
  }

  addItemToList() {
    if (this.newItem !== '' && !this.shoppingListItems.map(p => p.name).includes(this.newItem)) {
      let p = new Product();
      p.name = this.newItem;
      let item = new ShoppingListItem();
      item.productName = p.name;
      item.active = true;
      this.productRestService.saveShoppingListItem(item).subscribe({
        next: data => {
          p.id = data.id;
          this.shoppingListItems.push(p);
        },
        error: error => {
          console.log(error);
        }
      })
      this.newItem = '';
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: `Product ${this.newItem} is already in the list`,
        detail: ''
      });
      this.newItem = '';
    }
  }

  remove(itemId: string) {
    this.shoppingListItems.forEach(p => {
      if (p.id === itemId) {
        this.shoppingListItems.splice(this.shoppingListItems.indexOf(p), 1);
        this.productRestService.deleteShoppingListItem(itemId);
      }
    })
  }

  done(itemId: string) {
    this.shoppingListItems.forEach(p => {
      if (p.id === itemId) {
        p.done = true;
        let item = new ShoppingListItem();
        item.id = p.id;
        item.productName = p.name;
        item.active = !p.done;
        this.productRestService.updateShoppingListItemStatus(item);
      }
    })
  }

  undo(itemId: string) {
    this.shoppingListItems.forEach(p => {
      if (p.id === itemId) {
        p.done = false;
        let item = new ShoppingListItem();
        item.id = p.id;
        item.productName = p.name;
        item.active = !p.done;
        this.productRestService.updateShoppingListItemStatus(item);
      }
    })
  }
}

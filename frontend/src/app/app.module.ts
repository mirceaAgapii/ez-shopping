import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MainComponent } from './main/main.component';
import { MenuComponent } from './menu/menu.component';
import { LoginComponent } from './login/login.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { JWTTokenService } from './services/auth/jwttoken.service';
import { LocalStorageService } from './services/auth/storage/local-storage.service';
import { RegisterComponent } from './login/register/register.component';
import { ProductsComponent } from './products/products.component';
import { TokenInterceptorService } from './services/interceptor/token-interceptor.service';
import { MenubarModule } from 'primeng/menubar';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { BlockUIModule } from 'primeng/blockui';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { RippleModule } from 'primeng/ripple';
import { ButtonModule } from 'primeng/button';
import { AddProductComponent } from './admin/products-admin/add-product/add-product.component';
import { TreeTableModule } from 'primeng/treetable';
import { PrefetchUserResolver } from './guard/resolvers/prefetch-user.resolver';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { UsersAdminComponent } from './admin/users-admin/users-admin.component';
import { ProductsAdminComponent } from './admin/products-admin/products-admin.component';
import { StoresAdminComponent } from './admin/stores-admin/stores-admin.component';
import { AddNewUserComponent } from './admin/users-admin/add-new-user/add-new-user.component';
import { UserInfoComponent } from './admin/users-admin/user-info/user-info.component';
import { UserAccountComponent } from './user-account/user-account.component';
import { ProductInfoComponent } from './products/product-info/product-info.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { QRCodeComponent, QRCodeModule } from 'angular2-qrcode';
import { OrderStationComponent } from './order-station/order-station.component';
import { BasketComponent } from './basket/basket.component';
import { ShoppingListComponent } from './shopping-list/shopping-list.component';

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    MainComponent,
    MenuComponent,
    LoginComponent,
    RegisterComponent,
    ProductsComponent,
    AddProductComponent,
    AdminComponent,
    DashboardComponent,
    UsersAdminComponent,
    ProductsAdminComponent,
    StoresAdminComponent,
    AddNewUserComponent,
    UserInfoComponent,
    UserAccountComponent,
    ProductInfoComponent,
    CheckoutComponent,
    OrderStationComponent,
    BasketComponent,
    ShoppingListComponent

  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        AppRoutingModule,
        MatButtonModule,
        MatCardModule,
        MatDialogModule,
        MatIconModule,
        MatInputModule,
        MatTableModule,
        MatToolbarModule,
        MatMenuModule,
        MatProgressSpinnerModule,
        FormsModule,
        BrowserAnimationsModule,
        MenubarModule,
        ToastModule,
        BlockUIModule,
        ProgressSpinnerModule,
        TableModule,
        DropdownModule,
        DialogModule,
        RadioButtonModule,
        InputTextModule,
        InputTextareaModule,
        InputNumberModule,
        RippleModule,
        ButtonModule,
        TreeTableModule,
        NgbModule,
        QRCodeModule,
        ReactiveFormsModule
    ],
  exports: [
    MatButtonModule,
    MatCardModule,
    MatDialogModule,
    MatIconModule,
    MatInputModule,
    MatTableModule,
    MatToolbarModule,
    MatMenuModule,
    MatProgressSpinnerModule
  ],
  providers: [JWTTokenService,
  LocalStorageService,
  MessageService,
  {
    provide : HTTP_INTERCEPTORS,
    useClass : TokenInterceptorService,
    multi : true
  },
  PrefetchUserResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

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
import { MatMenuItem, MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { JWTTokenService } from './services/auth/jwttoken.service';
import { LocalStorageService } from './services/interceptor/storage/local-storage.service';
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
import { AddProductComponent } from './products/add-product/add-product.component';
import { TreeTableModule } from 'primeng/treetable';
import { TreeNode } from 'primeng/api';
import { LoadDataResolver } from './guard/load-data.resolver';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { UsersAdminComponent } from './admin/users-admin/users-admin.component';
import { ProductsAdminComponent } from './admin/products-admin/products-admin.component';
import { StoresAdminComponent } from './admin/stores-admin/stores-admin.component';

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
    StoresAdminComponent

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
    NgbModule
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
  LoadDataResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { ProductsAdminComponent } from './admin/products-admin/products-admin.component';
import { UsersAdminComponent } from './admin/users-admin/users-admin.component';
import { AppComponent } from './app.component';
import { BasketComponent } from './basket/basket.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { AdminPermisionsGuard } from './guard/admin-permisions.guard';
import { AuthorizationGuard } from './guard/authorization.guard';
import { CurrentUserResolver } from './guard/resolvers/current-user.resolver';
import { PrefetchProductsResolver } from './guard/resolvers/prefetch-products.resolver';
import { PrefetchUserResolver } from './guard/resolvers/prefetch-user.resolver';
import { PrefetchDashboardResolver } from './guard/resolvers/prefetch-dashboard.resolver';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './login/register/register.component';
import { MainComponent } from './main/main.component';
import { OrderStationComponent } from './order-station/order-station.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProductsComponent } from './products/products.component';
import { UserAccountComponent } from './user-account/user-account.component';
import {AddProductComponent} from "./admin/products-admin/add-product/add-product.component";
import {PrefetchPromoProductsResolver} from "./guard/resolvers/prefetch-promo-products.resolver";
import {ShoppingListComponent} from "./shopping-list/shopping-list.component";
import {PrefetchProductNamesResolver} from "./guard/resolvers/prefetch-product-names.resolver";
import {PrefetchShoppingListResolver} from "./guard/resolvers/prefetch-shopping-list.resolver";

export const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    children:[
    {
      path: 'users',
      component: UsersAdminComponent,
      resolve: {users : PrefetchUserResolver}
    },
    {
      path: 'products',
      component: ProductsAdminComponent,
      resolve: {products: PrefetchProductsResolver}
    },
    {
      path: 'dashboard',
      component: DashboardComponent,
      resolve: {systemCPU : PrefetchDashboardResolver}
    }
  ],
  canActivate: [AuthorizationGuard, AdminPermisionsGuard]
  },
  {
    path: 'account',
    component: UserAccountComponent,
    canActivate: [AuthorizationGuard],
    resolve: {user : CurrentUserResolver}
  },
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegisterComponent},
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: 'checkout',
    component: CheckoutComponent
  },
  {
    path: 'station',
    component: OrderStationComponent
  },
  {
    path: 'receiving',
    component: AddProductComponent
  },
  {
    path: 'basket',
    component: BasketComponent
  },
  {
    path: 'list',
    component: ShoppingListComponent,
    resolve: {
      productNames: PrefetchProductNamesResolver,
      shoppingList: PrefetchShoppingListResolver
    }
  },
  {
    path: '404',
    component: PageNotFoundComponent
  },
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthorizationGuard],
    resolve: {products: PrefetchPromoProductsResolver}
  },
  {
    path: '**',
    redirectTo: '/404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

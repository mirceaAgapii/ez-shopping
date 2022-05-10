import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { ProductsAdminComponent } from './admin/products-admin/products-admin.component';
import { UsersAdminComponent } from './admin/users-admin/users-admin.component';
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
import {WorkstationGuard} from "./guard/workstation-guard.service";

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
  canActivate: [AuthorizationGuard, AdminPermisionsGuard, WorkstationGuard]
  },
  {
    path: 'account',
    component: UserAccountComponent,
    canActivate: [AuthorizationGuard, WorkstationGuard],
    resolve: {user : CurrentUserResolver}
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'registration',
    component: RegisterComponent
  },
  {
    path: 'products',
    component: ProductsComponent,
    canActivate: [AuthorizationGuard]
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
    canActivate: [AuthorizationGuard]
  },
  {
    path: 'station',
    component: OrderStationComponent,
    canActivate: [AuthorizationGuard]
  },
  {
    path: 'receiving',
    component: AddProductComponent,
    canActivate: [AuthorizationGuard]
  },
  {
    path: 'basket',
    component: BasketComponent,
    canActivate: [AuthorizationGuard]
  },
  {
    path: 'list',
    component: ShoppingListComponent,
    resolve: {
      productNames: PrefetchProductNamesResolver,
      shoppingList: PrefetchShoppingListResolver
    },
    canActivate: [AuthorizationGuard, WorkstationGuard]
  },
  {
    path: '404',
    component: PageNotFoundComponent
  },
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthorizationGuard, WorkstationGuard],
    resolve: {products: PrefetchPromoProductsResolver},

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

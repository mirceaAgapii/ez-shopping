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
import { CurrentUserResolver } from './guard/current-user.resolver';
import { PrefetchProductsResolver } from './guard/prefetch-products.resolver';
import { PrefetchUserResolver } from './guard/prefetch-user.resolver';
import { PrefetchDashboardResolver } from './guard/resolvers/prefetch-dashboard.resolver';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './login/register/register.component';
import { MainComponent } from './main/main.component';
import { OrderStationComponent } from './order-station/order-station.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProductsComponent } from './products/products.component';
import { DashboardService } from './services/admin/dashboard.service';
import { AuthorizationService } from './services/auth/authorization.service';
import { UserAccountComponent } from './user-account/user-account.component';

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
    path: 'basket',
    component: BasketComponent
  },
  {path: '404', component: PageNotFoundComponent},
  {path: '', component: MainComponent, canActivate: [AuthorizationGuard]},
  
  {path: '**', redirectTo: '/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

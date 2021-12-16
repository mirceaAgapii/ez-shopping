import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { AuthorizationGuard } from './guard/authorization.guard';
import { LoadDataResolver } from './guard/load-data.resolver';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './login/register/register.component';
import { MainComponent } from './main/main.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ProductsComponent } from './products/products.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegisterComponent},
  {
    path: 'products', 
    component: ProductsComponent, 
    canActivate: [AuthorizationGuard], 
    resolve: {
      products: LoadDataResolver
    }
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

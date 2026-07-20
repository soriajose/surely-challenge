import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./features/home/pages/home-page/home-page.component').then((m) => m.HomePageComponent)
  },
  {
    path: 'store/:cartId',
    loadComponent: () =>
      import('./features/products/pages/store-page/store-page.component').then((m) => m.StorePageComponent)
  },
  {
    path: 'reports/top-products',
    loadComponent: () =>
      import('./features/reports/pages/top-products-page/top-products-page.component').then((m) => m.TopProductsPageComponent)
  }
];

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LocCommSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [LocCommSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class LocCommHomeModule {}

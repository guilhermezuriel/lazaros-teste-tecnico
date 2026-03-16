import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { ProfilesRoutingModule } from './profiles-routing.module';
import { ProfileListComponent } from './components/profile-list/profile-list.component';
import { ProfileFormComponent } from './components/profile-form/profile-form.component';
import { MaterialModule } from '../../shared/material/material.module';


@NgModule({
  declarations: [ProfileListComponent, ProfileFormComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule,
    ProfilesRoutingModule
  ],
  entryComponents: [ProfileFormComponent]
})
export class ProfilesModule { }

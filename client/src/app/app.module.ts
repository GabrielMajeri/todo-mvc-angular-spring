import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';

import { AppComponent } from './app.component';
import { ToDoCardComponent } from './to-do-card/to-do-card.component';

@NgModule({
  declarations: [AppComponent, ToDoCardComponent],
  imports: [BrowserAnimationsModule, MatCardModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}

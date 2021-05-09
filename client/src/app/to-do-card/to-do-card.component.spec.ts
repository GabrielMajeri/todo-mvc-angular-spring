import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { ToDo } from '../to-do';

import { ToDoCardComponent } from './to-do-card.component';

describe('ToDoCardComponent', () => {
  let component: ToDoCardComponent;
  let fixture: ComponentFixture<ToDoCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ToDoCardComponent],
      imports: [FormsModule, MatCardModule, MatCheckboxModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ToDoCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should display title and description', () => {
    const toDo: ToDo = {
      title: 'Test to do',
      description: 'Just a test',
      done: false,
    };

    component.toDo = toDo;
    fixture.detectChanges();

    const toDoCardElement: HTMLElement = fixture.nativeElement;

    const title = toDoCardElement.querySelector('mat-card-title mat-checkbox');
    expect(title?.textContent?.trim()).toBe(toDo.title);

    const description = toDoCardElement.querySelector('mat-card-content');
    expect(description?.textContent?.trim()).toBe(toDo.description);
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserStorageServiceComponent } from './user-storage-service.component';

describe('UserStorageServiceComponent', () => {
  let component: UserStorageServiceComponent;
  let fixture: ComponentFixture<UserStorageServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserStorageServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserStorageServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

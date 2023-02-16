import { TestBed } from '@angular/core/testing';

import { IntikompFormService } from './intikomp-form.service';

describe('IntikompFormService', () => {
  let service: IntikompFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IntikompFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'buy'
})
export class BuyPipe implements PipeTransform {

  transform(value: string): string {
    if(value==="B")
    {
      return value+'uy';
    }
    else
    {
      return value+'ell';
    }
  }

}

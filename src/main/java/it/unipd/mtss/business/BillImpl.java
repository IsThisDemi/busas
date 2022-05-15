////////////////////////////////////////////////////////////////////
// [RICCARDO] [MARTINELLO] [2009086]
// [SAMUELE] [RIZZATO] [1226307]
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.business;

import it.unipd.mtss.business.exception.BillException;
import it.unipd.mtss.model.EItem;
import it.unipd.mtss.model.ItemType;
import it.unipd.mtss.model.User;
import java.util.List;

public class BillImpl implements Bill{
    
    @Override
    public double getOrderPrice(List<EItem> itemsOrdered, User user) throws BillException {
        double total = 0;
        int processori = 0;
        int mouse = 0;
        int tastiere = 0;

        EItem ProcessoreMenoCostoso=null;
        EItem MouseMenoCostoso=null; 
        EItem TastieraMenoCostosa=null;
        EItem MouseOTastieraMenoCosto=null;
        if(itemsOrdered == null) {
            throw new BillException("Lista nulla");
        }

        if(itemsOrdered.isEmpty()) {
            throw new BillException("Lista ordini vuota");
        }
        
        for (EItem item : itemsOrdered) {
            total += item.getPrice();   
            if (item.getType() == ItemType.Processor) {
                processori++;

                if ((ProcessoreMenoCostoso == null) || (ProcessoreMenoCostoso.getPrice() > item.getPrice())) {
                    ProcessoreMenoCostoso = item;
                }
            }
            if (item.getType() == ItemType.Mouse) {
                mouse++;

                if ((MouseMenoCostoso == null) || (MouseMenoCostoso.getPrice() > item.getPrice())) {
                    MouseMenoCostoso = item;
                }
            }
            if (item.getType() == ItemType.Keyboard) {
                tastiere++;

                if ((TastieraMenoCostosa == null) || (TastieraMenoCostosa.getPrice() > item.getPrice())) {
                    TastieraMenoCostosa = item;
                }
            }

            if(item.getType() == ItemType.Keyboard || item.getType() == ItemType.Mouse){
                if ((MouseOTastieraMenoCosto == null) || (MouseOTastieraMenoCosto.getPrice() > item.getPrice())) {
                    MouseOTastieraMenoCosto = item;
                }
            }

            if (processori > 5) {
                total -= ProcessoreMenoCostoso.getPrice() * 0.5;
            }
            
            if (mouse > 10) {
                total -= MouseMenoCostoso.getPrice() * 1;
            }

            if (tastiere == mouse && tastiere != 0){
                total -= MouseOTastieraMenoCosto.getPrice() * 1;
            }
        }
        
        return total;
    }
}

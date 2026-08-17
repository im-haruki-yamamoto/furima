package in.tech_camp.furima.service;

import in.tech_camp.furima.entity.ItemEntity;
import in.tech_camp.furima.form.ItemEditForm;

public class ItemConverterService {


  public static ItemEditForm convertToEditForm(ItemEntity item) {
    if (item == null) {
      return null;
    }

    ItemEditForm form = new ItemEditForm();
    form.setId(item.getId());
    form.setName(item.getName());
    form.setDescription(item.getDescription());
    form.setCategory(item.getCategory());
    form.setCondition(item.getCondition());
    form.setDeliveryFee(item.getDeliveryFee());
    form.setPrefecture(item.getPrefecture());
    form.setUntilDelivery(item.getUntilDelivery());
    form.setPrice(item.getPrice());

    return form;
  }
}

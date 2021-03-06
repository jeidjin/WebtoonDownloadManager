package com.nodestory.utils;

import java.util.List;
import java.util.Map;

import com.nodestory.commons.Constants;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class ButtonCell extends ListCell<String> {

	public List<Map<String, Object>> copyList = null;
	public ListView<String> jobList = null;
	
	public Button downloadBtn;

	public ButtonCell(List<Map<String, Object>> fakeList, ListView<String> jobWebtoonList, Button downloadBtn) {
		this.copyList = fakeList;
		this.jobList = jobWebtoonList;
		this.downloadBtn = downloadBtn;
		
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (item != null) {
			final Button btn = new Button("제목: " + item);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					AlertSupport alert = new AlertSupport(Constants.AlertInfoMessage.REMOVE_ITEM);
					int sCount = alert.alertConfirm();
					if (sCount > 0) {
						// 작업리스트에서 해당 아이템을 제거한다.
						jobList.getItems().remove(getIndex());
						
						// fakeList 에서 해당 아이템을 삭제한다.
						copyList.remove(getIndex());
						
						if(copyList.size()  == 0) {
							downloadBtn.setDisable(true);
						}
					}
				}
			});
			setGraphic(btn);
		} else {
			setGraphic(null);
		}
	}

}



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.HttpClientUtil;
import utils.HttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FXMLExampleController {
    @FXML
    private Text actiontarget;
    @FXML
    private TextArea respone;
    @FXML
    TextField  uuid;
    @FXML
    protected void upload(ActionEvent event) throws Exception {

  //上传文件
        Stage stage = (Stage) actiontarget.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file=fileChooser.showOpenDialog(stage);

        String respone=HttpClientUtil.getInstance().doUpload("http://localhost:8081/file","file", file.getPath());
       System.out.println(respone);


    }

    //下载文件
    @FXML
    protected void download(ActionEvent event) throws Exception {
        String UUID=uuid.getText();
         Map<String, Object> params = new HashMap<String, Object>();
        params.put("uuid",UUID);
        String res=HttpClientUtil.getInstance().doGet("http://localhost:8081/file",params);
        respone.setText(res);
    }
    //获取文件信息
    @FXML
    protected void getfilemsg(ActionEvent event) throws Exception {
        String UUID=uuid.getText();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("uuid",UUID);
        String res=HttpClientUtil.getInstance().doGet("http://localhost:8081/file/msg",params);
        respone.setText(res);
    }
}

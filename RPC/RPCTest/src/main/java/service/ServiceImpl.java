package service;

public class ServiceImpl implements Service {

    @Override
    public String declareIndependence(String data) {
        return "ok,兄弟们全体目光看向我，看我看我，我宣布个事，我是个" + data + "!!!";
    }
}

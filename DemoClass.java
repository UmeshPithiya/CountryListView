package com.example.umesh.countrylistview;

/**
 * Created by umesh on 28/03/16.
 */
public class DemoClass {
    IDemoInterface intf;
    public DemoClass(IDemoInterface i)
    {
        intf=i;
    }
    public void onDelete()
    {
        intf.delete();
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CountLives : MonoBehaviour
{
    public static int Live = 3;
    public Text textLive;
    // Start is called before the first frame update
    void Start()
    {
        textLive = GetComponent<Text>();
    }

    // Update is called once per frame
    void Update()
    {
        textLive.text = "" + Live;
    }
}

using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Collect : MonoBehaviour
{
    public static int TheCherry;
    public Text textCherry;
    // Start is called before the first frame update
    void Start()
    {
        textCherry = GetComponent<Text>();
    }

    // Update is called once per frame
    void Update()
    {
        textCherry.text = "" + TheCherry;
    }
}

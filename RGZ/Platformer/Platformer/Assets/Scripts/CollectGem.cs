using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CollectGem : MonoBehaviour
{
    public static int TheGem;
    public Text textGem;
    // Start is called before the first frame update
    void Start()
    {
        textGem = GetComponent<Text>();
    }

    // Update is called once per frame
    void Update()
    {
        textGem.text = "" + TheGem;
    }
}

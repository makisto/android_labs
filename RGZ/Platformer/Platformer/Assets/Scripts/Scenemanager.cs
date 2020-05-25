using System.Collections;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
using UnityEngine;

public class Scenemanager : MonoBehaviour
{
    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.name == "EndLevel1")
        {
            SceneManager.LoadScene("Scene02");
        }
        if (collision.gameObject.name == "EndGame")
        {
            if(Collect.TheCherry == 20 && CollectGem.TheGem == 10)
            {
                SceneManager.LoadScene("GoodEnding");
            }
            else
            {
                SceneManager.LoadScene("BadEnding2");
            }
        }
    }
}

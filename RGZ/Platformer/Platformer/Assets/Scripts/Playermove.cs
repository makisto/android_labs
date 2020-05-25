using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Security.Cryptography;
using System.Threading;
using UnityEngine.SceneManagement;
using UnityEngine;

public class Playermove : MonoBehaviour
{
    public CharacterController2D controller;

    public Animator animator;

    public float runSpeed = 20f;
    float horizontalMove = 0f;

    float SX, SY;

    bool jump = false;
    bool crouch = false;

    void Start()
    {
        SX = transform.position.x;
        SY = transform.position.y;
    }

    void Update()
    {
        horizontalMove = Input.GetAxisRaw("Horizontal") * runSpeed;

        animator.SetFloat("Run", Math.Abs(horizontalMove));

        if(Input.GetButtonDown("Jump"))
        {
            jump = true;
            animator.SetBool("isJumping", true);
        }
        if (Input.GetButtonDown("Crouch"))
        {
            crouch = true;
        }
        else if (Input.GetButtonUp("Crouch"))
        {
            crouch = false;
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.name.Equals("Platform"))
        {
            this.transform.parent = collision.transform;
        }
        if (collision.gameObject.name == "DeadSpace" || collision.gameObject.name == "Opossum" || collision.gameObject.name == "Eagle")
        {
            Collect.TheCherry = 0;
            CollectGem.TheGem = 0;
            CountLives.Live -= 1;
            if(CountLives.Live == 0)
            {
                SceneManager.LoadScene("BadEnding1");
            }
            else
            {
                SceneManager.LoadScene("SampleScene");
            }
            transform.position = new Vector3(SX, SY, transform.position.z);
        }
    }

    private void OnCollisionExit2D(Collision2D collision)
    {
        if (collision.gameObject.name.Equals("Platform"))
        {
            this.transform.parent = null;
        }
    }

    public void OnCrouch(bool isCrouch)
    {
        animator.SetBool("isCrouch", isCrouch);
    }

    public void OnLanding()
    {
        animator.SetBool("isJumping", false);
    }

    private void FixedUpdate()
    {
        controller.Move(horizontalMove * Time.deltaTime, crouch, jump);

        jump = false;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.tag == "Cherry")
        {
            Collect.TheCherry += 1;
            Destroy(collision.gameObject);
        }
        if (collision.tag == "Gem")
        {
            CollectGem.TheGem += 1;
            Destroy(collision.gameObject);
        }
    }
}

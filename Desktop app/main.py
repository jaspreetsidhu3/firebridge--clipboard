import tkinter

arr = []


def setupkey():
    from firebase import firebase
    firebase = firebase.FirebaseApplication('Add Firebase URL here', None)
    result = firebase.get('/Clipboard/', '')
    arr.clear()
    p = 0
    try:
        for j in reversed(result.keys()):
            p = p + 1
            arr.insert(p, j)
    except Exception as e:
        print(e)


def add_list_function(list):
    from firebase import firebase
    firebase = firebase.FirebaseApplication('Add Firebase URL here', None)
    result = firebase.get('/Clipboard/', '')
    setupkey()
    k = 0
    try:
        for i in reversed(result.values()):
            k = k + 1
            list.insert(k, f"{k}. {i}")
    except Exception as e:
        print(e)


def refresh():
    window.destroy()
    onStart()


def add_data(ned):
    from firebase import firebase
    firebase = firebase.FirebaseApplication('Add Firebase URL here', None)
    firebase.post('/Clipboard/', str(ned.get()))
    refresh()


def remove_data(dele):
    from firebase import firebase
    firebase = firebase.FirebaseApplication('Add Firebase URL here', None)
    index = (int)(dele.get())
    firebase.delete('/Clipboard', arr[index - 1])
    refresh()


def onStart():
    global window
    global new_data
    global delete_data
    window = tkinter.Tk()
    new_data = tkinter.StringVar()
    delete_data = tkinter.StringVar()
    # Code to add widgets will go here...
    window.title("ClipBoard")
    window.minsize(400, 600)
    window.maxsize(500, 800)
    reload = tkinter.Button(window, text='Reload', width=25, command=refresh)
    reload.pack(ipady=13, pady=23)
    Lb = tkinter.Listbox(window)
    add_list_function(Lb)
    Lb.pack(ipadx=90)
    e1 = tkinter.Entry(window, width=33, textvariable=new_data)
    e1.pack(ipadx=23, ipady=13, pady=23)
    addbutton = tkinter.Button(window, text='Add', width=25, command=lambda: add_data(new_data))
    addbutton.pack(pady=23)

    can = tkinter.Canvas(window, height=100, width=100)
    e2 = tkinter.Entry(can, width=13, textvariable=delete_data)
    removebtn = tkinter.Button(can, text="Delete", width=15, command=lambda: remove_data(delete_data))
    e2.pack(pady=9, side=tkinter.LEFT)
    removebtn.pack(pady=9, side=tkinter.LEFT)
    can.place(relx=0.5, rely=0.8, anchor=tkinter.CENTER)
    window.mainloop()


if __name__ == '__main__':
    onStart()
    print(arr)

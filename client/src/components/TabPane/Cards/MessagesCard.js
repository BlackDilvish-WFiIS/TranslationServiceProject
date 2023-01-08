import React, { useState, useEffect } from "react";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Button, Row, Modal, Input, message, Select  } from "antd";
import { Table } from "ant-table-extensions";
import { baseURL } from "../../../config";

const MessagesCard = () => {
  const [data, setData] = useState();
  const [messageApi, resultMessageHolder] = message.useMessage();

  const [isEditing, setIsEditing] = useState(false);
  const [isAdding, setIsAdding] = useState(false);
  const [editingMessage, setEditingMessage] = useState(null);
  const [addingMessage, setAddingMessage] = useState(null);

  const [loading, setLoading] = useState(false);
  const [tableParams, setTableParams] = useState({
    pagination: {
      pageSize: 10,
    },
  });

  const [langData, setLangData] = useState();
  const [originalMessData, setoriginalMessData] = useState();
  const [tagData, setTagData] = useState();

  const success = (message) => {
    messageApi.open({
      type: "success",
      content: `${message}`,
      style: {
        marginTop: "150px",
      },
      duration: 2,
    });
  };

  const error = (message) => {
    messageApi.open({
      type: "error",
      content: `${message}`,
      style: {
        marginTop: "150px",
      },
      duration: 2,
    });
  };

  const onAdd = () => {
    setIsAdding(true);
    setAddingMessage({ ...editingMessage });
  };

  const fetchData = () => {
    setLoading(true);
    fetchLanguages();
    fetchTags();
    fetch(`${baseURL}/message`)
      .then((response) => response.json())
      .then((data) => {
        for (let i = 0; i < data.length; i++) {
          const orig = data.find(e => e.id === data[i].original_message);
          if (orig)
          {
            data[i]['original_message_text'] = orig.content;
          }
          else
          {
            data[i]['original_message_text'] = "";
          }
        }
        setData(data);
        getOriginalMessages(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error while fetching data: ", error);
      });
  };

  const handleEdit = (record) => {
    setIsEditing(true);
    setEditingMessage({ ...record });
  };

  const handleDelete = (record) => {
    Modal.confirm({
      title: "Are you sure, you want to delete this message?",
      okText: "Yes",
      okType: "danger",
      onOk: () => {
        deleteMessage(record.id);
      },
    });
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(() => {
      fetchData();
    }, 30000);
    return () => clearInterval(interval);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [JSON.stringify(tableParams)]);
  const handleTableChange = (pagination, filters, sorter) => {
    fetchData();
    setTableParams({
      pagination,
      filters,
      ...sorter,
    });
  };

  const resetEditing = () => {
    setIsEditing(false);
    setEditingMessage(null);
  };

  const resetAdding = () => {
    setIsAdding(false);
    setAddingMessage(null);
  };

  const columns = [
    {
      title: "Message content",
      dataIndex: "content",
      sorter: {
        compare: (a, b) => a.content.localeCompare(b.content),
      },
      editable: true,
    },
    {
      title: "Language",
      dataIndex: "language",
      sorter: {
        compare: (a, b) => a.language.localeCompare(b.language),
      },
      editable: true,
    },
    {
      title: "Original message",
      dataIndex: "original_message_text",
      sorter: {
        compare: (a, b) => a.original_message_text.localeCompare(b.original_message_text),
      },
      editable: true,
    },
    {
      title: "Tags",
      dataIndex: "tags",
      editable: true,
    },
    {
      title: "Operations",
      render: (record) => {
        return (
          <>
            <EditOutlined
              onClick={() => {
                handleEdit(record);
              }}
            />
            <DeleteOutlined
              onClick={() => {
                handleDelete(record);
              }}
              style={{ color: "red", marginLeft: 12 }}
            />
          </>
        );
      },
    },
  ];

  const deleteMessage = (id) => {
    fetch(`${baseURL}/message/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        fetchData();
        if (response.ok) return response;
        else return response.json();
      })
      .then((res) => {
        if (res.ok) {
          success("Message deleted successfully!");
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while deleting: ", err);
        error("Error! Couldn't delete message");
      });
  };

  const saveEdited = () => {
    fetch(`${baseURL}/message`, {
      method: "PUT",
      headers: {
        Accept: "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
      },
      body: JSON.stringify(editingMessage),
    })
      .then((response) => {
        fetchData();
        return response.json();
      })
      .then((res) => {
        if (res.id) {
          success("Message edited successfully!");
          resetEditing();
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while edditing message: ", err);
        error("Error! Couldn't edit message");
      });
  };

  const saveNewMessage = () => {
    fetch(`${baseURL}/message`, {
      method: "POST",
      headers: {
        Accept: "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
      },
      body: JSON.stringify(addingMessage),
    })
      .then((response) => {
        fetchData();
        return response.json();
      })
      .then((res) => {
        if (res.id) {
          success("New message added successfully!");
          resetAdding();
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while adding new message: ", err);
        error("Error! Couldn't add message");
      });
  };

  const fetchLanguages = () => {
    fetch(`${baseURL}/language`)
      .then((response) => response.json())
      .then((data) => {
        for (let i = 0; i < data.length; i++) {
          data[i]['label'] = data[i]['language'];
          data[i]['value'] = data[i]['language'];
        }
        if (data.length === 0)
        {
          data.push({'label': 'English', 'value': 'English'});
        }
        setLangData(data);
      })
      .catch((error) => {
        console.error("Error while fetching langs: ", error);
      });
  };

  const getOriginalMessages = (data) => {
    let originals = []
    for (let i = 0; i < data.length; i++) {
      if (data[i]['original_message'] == null)
      {
        let newData = { 'label': data[i]['content'], 'value': data[i]['id']};
        originals.push(newData);
      }
    }
    originals.push({ 'label': 'None', 'value': null});
    setoriginalMessData(originals);
  }

  const fetchTags = () => {
    fetch(`${baseURL}/tag`)
      .then((response) => response.json())
      .then((data) => {
        for (let i = 0; i < data.length; i++) {
          data[i]['label'] = data[i]['tag'];
          data[i]['value'] = data[i]['tag'];
        }
        setTagData(data);
      })
      .catch((error) => {
        console.error("Error while fetching tags: ", error);
      });
  }

  return (
    <>
      {resultMessageHolder}
      <Row justify="end">
        <Button
          onClick={onAdd}
          type="primary"
          icon={<PlusOutlined />}
          style={{ marginBottom: 16 }}
        >
          Add a new message
        </Button>
      </Row>

      <Table
        searchable
        searchableProps={{
          fuzzySearch: true,
          fuzzyProps: {
            ignoreLocation: true,
            includeScore: true,
            threshold: 0.0,
          },
        }}
        columns={columns}
        dataSource={data}
        rowKey={(record) => record.id}
        pagination={tableParams.pagination}
        loading={loading}
        size="middle"
        onChange={handleTableChange}
      />

      <Modal
        title="Edit message"
        open={isEditing}
        okText="Save"
        onCancel={() => {
          resetEditing();
        }}
        onOk={saveEdited}
      >
        <Select
          style={{
            width: '100%',
          }}
          value={editingMessage?.language}
          placeholder="Please add language"
          onChange={(e) => {
            setEditingMessage((pre) => {
              return { ...pre, language: e };
            });
          }}
          options={langData}
        />
        <br/>
        <Select
          style={{
            width: '100%',
          }}
          value={editingMessage?.original_message}
          placeholder="Please add original message"
          onChange={(e) => {
            setEditingMessage((pre) => {
              return { ...pre, original_message: e };
            });
          }}
          options={originalMessData}
        />
        <br/>
        <Select
          mode="multiple"
          allowClear
          style={{
            width: '100%',
          }}
          value={editingMessage?.tags}
          placeholder="Please select tags"
          onChange={(e) => {
            setEditingMessage((pre) => {
              console.log(e);
              return { ...pre, tags: e };
            });
          }}
          options={tagData}
        />
        <Input
          value={editingMessage?.content}
          placeholder="Message content"
          onChange={(e) => {
            setEditingMessage((pre) => {
              return { ...pre, content: e.target.value };
            });
          }}
        ></Input>
      </Modal>

      <Modal
        title="Add new message"
        open={isAdding}
        okText="Save"
        onCancel={() => {
          resetAdding();
        }}
        onOk={saveNewMessage}
      >
        <Select
          style={{
            width: '100%',
          }}
          value={addingMessage?.language}
          placeholder="Please add language"
          onChange={(e) => {
            setAddingMessage((pre) => {
              return { ...pre, language: e };
            });
          }}
          options={langData}
        />
        <br/>
        <Select
          style={{
            width: '100%',
          }}
          value={addingMessage?.original_message}
          placeholder="Please add original message"
          onChange={(e) => {
            setAddingMessage((pre) => {
              return { ...pre, original_message: e };
            });
          }}
          options={originalMessData}
        />
        <br/>
        <Select
          mode="multiple"
          allowClear
          style={{
            width: '100%',
          }}
          value={addingMessage?.tags}
          placeholder="Please select tags"
          onChange={(e) => {
            setAddingMessage((pre) => {
              return { ...pre, tags: e };
            });
          }}
          options={tagData}
        />
        <Input
          value={addingMessage?.content}
          placeholder="Message content"
          onChange={(e) => {
            setAddingMessage((pre) => {
              return { ...pre, content: e.target.value };
            });
          }}
        ></Input>
      </Modal>
    </>
  );
};

export default MessagesCard;

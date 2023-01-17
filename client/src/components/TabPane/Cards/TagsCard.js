import React, { useState, useEffect } from "react";
import { PlusOutlined, EditOutlined, DeleteOutlined } from "@ant-design/icons";
import { Button, Row, Modal, Input, message } from "antd";
import { Table } from "ant-table-extensions";
import { baseURL } from "../../../config";
import Tag from "../../Tag/Tag";

const TagsCard = () => {
  const [data, setData] = useState();
  const [messageApi, resultMessageHolder] = message.useMessage();

  const [isEditing, setIsEditing] = useState(false);
  const [isAdding, setIsAdding] = useState(false);
  const [editingTag, setEditingTag] = useState(null);
  const [addingTag, setAddingTag] = useState(null);

  const [loading, setLoading] = useState(false);
  const [tableParams, setTableParams] = useState({
    pagination: {
      pageSize: 10,
    },
  });

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

  const columns = [
    {
      title: "Tag name",
      dataIndex: "tag",
      sorter: {
        compare: (a, b) => a.tag.localeCompare(b.tag),
      },
      render: (record) => {
        return <Tag text={record} />;
      },
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

  const fetchData = () => {
    setLoading(true);
    fetch(`${baseURL}/tag`)
      .then((response) => response.json())
      .then((data) => {
        setData(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error while fetching data: ", error);
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

  const deleteTag = (id) => {
    fetch(`${baseURL}/tag/${id}`, {
      method: "DELETE",
    })
      .then((response) => {
        fetchData();
        if (response.ok) return response;
        else return response.json();
      })
      .then((res) => {
        if (res.ok) {
          success("Tag deleted successfully!");
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while deleting: ", err);
        error("Error! Couldn't delete tag");
      });
  };

  const saveNewTag = () => {
    fetch(`${baseURL}/tag`, {
      method: "POST",
      headers: {
        Accept: "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
      },
      body: JSON.stringify(addingTag),
    })
      .then((response) => {
        fetchData();
        return response.json();
      })
      .then((res) => {
        if (res.tag) {
          success("New tag added successfully!");
          resetAdding();
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while adding new tag: ", err);
        error("Error! Couldn't add tag");
      });
  };

  const saveEdited = () => {
    fetch(`${baseURL}/tag`, {
      method: "POST",
      headers: {
        Accept: "application/json, text/plain",
        "Content-Type": "application/json;charset=UTF-8",
      },
      body: JSON.stringify(editingTag),
    })
      .then((response) => {
        fetchData();
        return response.json();
      })
      .then((res) => {
        if (res.tag) {
          success("Tag edited successfully!");
          resetEditing();
        } else {
          error(res.message);
        }
      })
      .catch((err) => {
        console.log("Error while edditing tag: ", err);
        error("Error! Couldn't edit tag");
      });
  };

  const onAdd = () => {
    setIsAdding(true);
    setAddingTag({ ...editingTag });
  };

  const handleDelete = (record) => {
    Modal.confirm({
      title: "Are you sure, you want to delete this tag record?",
      okText: "Yes",
      okType: "danger",
      onOk: () => {
        deleteTag(record.id);
      },
    });
  };

  const resetEditing = () => {
    setIsEditing(false);
    setEditingTag(null);
  };

  const resetAdding = () => {
    setIsAdding(false);
    setAddingTag(null);
  };

  const handleEdit = (record) => {
    setIsEditing(true);
    setEditingTag({ ...record });
  };

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
          Add a new tag
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
        title="Edit tag"
        open={isEditing}
        okText="Save"
        onCancel={() => {
          resetEditing();
        }}
        onOk={saveEdited}
      >
        <Input
          value={editingTag?.tag}
          onChange={(e) => {
            setEditingTag((pre) => {
              return { ...pre, tag: e.target.value };
            });
          }}
        ></Input>
      </Modal>

      <Modal
        title="Add new tag"
        open={isAdding}
        okText="Save"
        onCancel={() => {
          resetAdding();
        }}
        onOk={saveNewTag}
      >
        <Input
          value={addingTag?.tag}
          placeholder="Tag name"
          onChange={(e) => {
            setAddingTag((pre) => {
              return { ...pre, tag: e.target.value };
            });
          }}
        ></Input>
      </Modal>
    </>
  );
};

export default TagsCard;

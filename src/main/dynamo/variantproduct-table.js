var params = {
  TableName: "variantproduct",
  KeySchema: [
    {AttributeName: "id", KeyType: "HASH"},
  ],
  AttributeDefinitions: [
    {AttributeName: "id", AttributeType: "S"},
    {AttributeName: "tenant_id", AttributeType: "S"},
    {AttributeName: "productRef", AttributeType: "S"},
    {AttributeName: "gtin", AttributeType: "S"},
    {AttributeName: "isbn", AttributeType: "S"},
    {AttributeName: "baseProductId", AttributeType: "S"},
    {AttributeName: "baseProductRef", AttributeType: "S"}
  ],
  ProvisionedThroughput: {
    ReadCapacityUnits: 10,
    WriteCapacityUnits: 10
  },

  GlobalSecondaryIndexes: [
    {
      IndexName: 'product_tenantId_index', /* required */
      KeySchema: [ /* required */
        {
          AttributeName: "tenant_id", KeyType: 'HASH' /* required */
        }
      ],
      Projection: { /* required */
        ProjectionType: 'ALL'
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 10,
        WriteCapacityUnits: 10
      }
    },
    {
      IndexName: 'product_productRef_index', /* required */
      KeySchema: [ /* required */
        {
          AttributeName: "productRef", KeyType: 'HASH' /* required */
        }
      ],
      Projection: { /* required */
        ProjectionType: 'ALL'
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 10,
        WriteCapacityUnits: 10
      },
    },
    {
      IndexName: 'product_gtin_index', /* required */
      KeySchema: [ /* required */
        {
          AttributeName: "gtin", KeyType: 'HASH' /* required */
        }
      ],
      Projection: { /* required */
        ProjectionType: 'ALL'
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 10,
        WriteCapacityUnits: 10
      },
    },
    {
      IndexName: 'product_isbn_index', /* required */
      KeySchema: [ /* required */
        {
          AttributeName: "isbn", KeyType: 'HASH' /* required */
        }
      ],
      Projection: { /* required */
        ProjectionType: 'ALL'
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 10,
        WriteCapacityUnits: 10
      },
    },
    {
      IndexName: 'product_baseProductId_index', /* required */
      KeySchema: [ /* required */
        {
          AttributeName: "baseProductId", KeyType: 'HASH' /* required */
        },
        {
          AttributeName: "baseProductRef", KeyType: 'RANGE' /* required */
        }
      ],
      Projection: { /* required */
        ProjectionType: 'ALL'
      },
      ProvisionedThroughput: {
        ReadCapacityUnits: 10,
        WriteCapacityUnits: 10
      },
    }
  ]
};

dynamodb.createTable(params, function (err, data) {
  if (err) console.log(err, err.stack);
  else console.log(JSON.stringify(data, null, 2));

});
